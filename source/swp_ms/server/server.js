const express = require("express");
const app = express();
const port = 3003; // <- 3000에서 다른 숫자로 변경
const cors = require("cors");
const bodyParser = require("body-parser");
const mysql = require("mysql");
const dbconfig = require('../config/database.js');
const connection = mysql.createConnection(dbconfig);


app.use(cors());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.get("/", (req, res) => {
  res.send("Hello World!");
});

app.post("/Feedback", (req, res) => {
  let keywordArray = [];
  let cnt = 0;
  connection.query('select * from product_info', function(err, result_keyword){
    if(err) throw err;

    for(let i=0;i<21;i++){
      keywordArray.push([]);
      keywordArray[i].push(result_keyword[cnt].keyword);
      for(let j=1;j<=10;j++){
        keywordArray[i].push(result_keyword[cnt].product_name);
        cnt++;
      }
    }
    res.send({keywordArray: keywordArray});
  });
});

app.post('/api1', (req, res) => {
  let product_name = req.body.product;
  let allcount =[];
  let seecount = [];
  connection.query("select a.keyword, a.product_name, b.age,b.gender,count(b.age) as allcount from ad_log as a"+
    " inner join user_info as b on a.ad_num=b.product_num and a.product_name='"+product_name+"'"+
    " inner join setting as c on c.keyword=a.keyword and c.age=b.age and c.gender=b.gender"+
    " group by age"+
    " order by age;",function(err,rows){
    if(err) throw err;

    connection.query( "select a.keyword, a.product_name, b.age,b.gender,count(b.age) as seecount from ad_log as a"+
    " inner join user_info as b on a.ad_num=b.product_num and a.product_name='"+product_name+"'"+
    " and b.see_time>=4.0 inner join setting as c on c.keyword=a.keyword and c.age=b.age and c.gender=b.gender"+
    " group by age"+
    " order by age;",function(err2,rows2){
      if(err2)  throw err;

      for (let i = 0; i < rows.length; i++) {
        allcount.push([]);
        allcount[i].push(rows[i].keyword);
        allcount[i].push(rows[i].product_name);
        allcount[i].push(rows[i].age);
        allcount[i].push(rows[i].gender);
        allcount[i].push(rows[i].allcount);
      }

      for (let i = 0; i < rows2.length; i++) {
        seecount.push([]);
        seecount[i].push(rows2[i].keyword);
        seecount[i].push(rows2[i].product_name);
        seecount[i].push(rows2[i].age);
        seecount[i].push(rows2[i].gender);
        seecount[i].push(rows2[i].seecount);
      }
      res.send({allcount : allcount, seecount : seecount});
    });
  });
});

app.post('/api2', (req, res) => {
  let product_name = req.body.product;
  let allcount =[];
  let seecount = [];
  connection.query("select a.keyword, a.product_name, c.age, c.gender,count(c.age) as whole from product_info as a "+
  "inner join ad_log as b on a.product_name = b.product_name inner join user_info as c on b.ad_num = c.product_num and b.product_name = '"+product_name+"' group by age,gender;"
  , function(err,rows){
    if(err) throw err;

    connection.query("select a.keyword, a.product_name, c.age, c.gender,count(c.age) as whole from product_info as a "+
    "inner join ad_log as b on a.product_name = b.product_name inner join user_info as c on b.ad_num = c.product_num and b.product_name = '"+product_name+"' and c.see_time >=4.0 "+
    "group by age,gender;", function(err2,rows2){
      if(err2)  throw err;

      for (let i = 0; i < rows.length; i++) {
        allcount.push([]);
        allcount[i].push(rows[i].keyword);
        allcount[i].push(rows[i].product_name);
        allcount[i].push(rows[i].age);
        allcount[i].push(rows[i].gender);
        allcount[i].push(rows[i].whole);
      }

      for (let i = 0; i < rows2.length; i++) {
        seecount.push([]);
        seecount[i].push(rows2[i].keyword);
        seecount[i].push(rows2[i].product_name);
        seecount[i].push(rows2[i].age);
        seecount[i].push(rows2[i].gender);
        seecount[i].push(rows2[i].whole);
      }
      res.send({allcount : allcount, seecount : seecount});
    });
  });
});

app.post('/api3', (req, res) => {
  let product_name = req.body.product;
  let avgTime = [];

  connection.query("select a.keyword, a.product_name, c.age, c.gender,avg(c.see_time) as avg_time from product_info as a " +
  "inner join ad_log as b on a.product_name = b.product_name inner join user_info as c on b.ad_num = c.product_num and b.product_name = '"+ product_name +"' "+ 
  "group by age,gender;", function(err,rows){
    if(err)  throw err;

    for (let i = 0; i < rows.length; i++) {
      avgTime.push([]);
      avgTime[i].push(rows[i].keyword);
      avgTime[i].push(rows[i].product_name);
      avgTime[i].push(rows[i].age);
      avgTime[i].push(rows[i].gender);
      avgTime[i].push(rows[i].avg_time);
    }
    res.send({avgTime : avgTime});
  });
});

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`);
});