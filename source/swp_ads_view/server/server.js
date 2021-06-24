const express = require("express");
const app = express();
const port = 3001; // <- 3000에서 다른 숫자로 변경
const cors = require("cors");
const bodyParser = require("body-parser");
const mysql = require("mysql");
const dbconfig = require('../config/database.js');
const connection = mysql.createConnection(dbconfig);
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cors());
app.use(bodyParser.json());

app.get("/", (req, res) => {
  res.send("Hello World!");
});

let age = 0;
let gender = '0';
let check;
let check_req = false

app.post("/check1", (req, res) => {
  check=req.body.check_req;
  console.log(check);
  if (check == true){
    console.log("true");
    check_req = true
    res.sendStatus(200);
  }
  else if (check == undefined){
    console.log("false");
    res.send({
      check_req:check_req
    });
    check_req = false
  }
});

let check_age;
let check_gender;
let count = 0;
connection.query("truncate user_info");
connection.query("truncate ad_log");


app.post("/Ads_img1", (req, res) => {

      check_age = req.body.age;
      check_gender = req.body.gender;
      if(check_age != undefined && check_gender != undefined) {
        if(check_age == 0){
          age = 20;
        }
        else age = check_age;
        if (check_gender == '0'){
          gender = 'F';
        }
        else gender = check_gender;
        console.log(age + " " + gender);
        res.sendStatus(200);
      }

   else if(check_age==undefined&& check_gender==undefined){
    let keywordArray = []; // age, gender에 따른 keyword 배열
    connection.query("SELECT keyword FROM setting where age = " + age + " and gender = '" + gender + "'", function(err, result_keyword){
      if(err) throw err;
    
      result_keyword.forEach(function(element){ // 질의한 후 keyword 배열에 추가
        keywordArray.push(element.keyword);
      });
    let randomKeyword = Math.floor(Math.random() * keywordArray.length); // 성별, 나이를 통해 얻은 여러 키워드 중 하나의 키워드 뽑기
    let randomResult = Math.floor(Math.random() * 10); // 선택된 하나의 키워드의 물품들 중 하나의 물품 뽑기
    let keyword = keywordArray[randomKeyword]; // 선택된 키워드
    connection.query("SELECT * FROM product_info where keyword = '" + keyword + "'", function(err, result_prod){
      if(err) throw err;
      console.log(keyword);
      let name = result_prod[randomResult].product_name; // 선택된 물품의 이름
      let url = result_prod[randomResult].url; // 선택된 물품의 URL
      let brand = result_prod[randomResult].product_brand; // 선택된 물품의 브랜드명
      let price = result_prod[randomResult].product_price; // 선택된 물품의 가격
      count = count + 1;
      connection.query("INSERT INTO ad_log VALUES('" +count+"','" +name+"','"+keyword+"')");
      console.log("키워드"+keyword);
      console.log("제품이름"+name);
      res.send({prod_name:name, prod_url:url, prod_brand:brand, prod_price:price});
    });
  });
}
});

app.listen(port, () => {
  console.log(`server listening at http://localhost:${port}`);
});
