import React from 'react';
import { Bar } from "react-chartjs-2";
import Helmet from 'react-helmet';

class Ms3 extends React.Component{    
    constructor(props) {
        super(props);

        fetch("http://localhost:3003/Feedback", { 
          method: "post", //통신방법
          headers: {
            "content-type": "application/json",
          },
          body: JSON.stringify()
        })
        .then((res) => res.json())
        .then((json) => {
          this.setState({
            keywordArray : json.keywordArray
          })
        });
    
        this.state = {
          keywordArray:[[]],
          KEYWORD:'',
          PRODUCT:'',
          avgTime:[[]],
        };
    }
    
    categoryChange = (e) => {
        var count =0;
        var temp = e.target.value;
        
        var product_name = temp.split(',');

        for(var i =0; i<21; i++)
        {
            if(product_name[0] === this.state.keywordArray[i][0])
            {
                count = i;
            }
        }

        this.setState((state) => {
            return{
                KEYWORD : product_name[0]
            }
        });
   
        var test1 = document.getElementById("option2");

        if(product_name.length <=50)
        {
            for (let i=1; i<11; i++) {
                if (i===1) {
                    test1.innerHTML = "<option>"+product_name[1]+"</option>";
                }
                else
                {
                    test1.innerHTML += "<option>"+this.state.keywordArray[count][i]+"</option>";
                }
            }
        }
        else
        {
            test1.innerHTML = "<option>선택해주세요</option>";
        }

    }


    productChange = (e) => {
        var product_name2 = e.target.value;
        this.setState((state) => {
            return{
                PRODUCT : product_name2
            }
        })
    }
    render(){
        var male = [0,0,0,0,0,0,0];
        var female = [0,0,0,0,0,0,0];

        for (let i = 0; i < this.state.avgTime.length; i++) {
            if (this.state.avgTime[i][2] === 10) {
                if (this.state.avgTime[i][3] === 'M') 
                    male[1] = this.state.avgTime[i][4];
                else
                    female[1] = this.state.avgTime[i][4];
            }
            else if (this.state.avgTime[i][2] === 20) {
                if (this.state.avgTime[i][3] === 'M') 
                     male[2] = this.state.avgTime[i][4];
                else
                    female[2] = this.state.avgTime[i][4];
            }
            else if (this.state.avgTime[i][2] === 30) {
                if (this.state.avgTime[i][3] === 'M') 
                    male[3] = this.state.avgTime[i][4];
                else
                    female[3] = this.state.avgTime[i][4];
            }
            else if (this.state.avgTime[i][2] === 40) {
                if (this.state.avgTime[i][3] === 'M') 
                    male[4] = this.state.avgTime[i][4];
                else
                    female[4] = this.state.avgTime[i][4];
            }
            else if (this.state.avgTime[i][2] === 50) {
                if (this.state.avgTime[i][3] === 'M') 
                    male[5] = this.state.avgTime[i][4];
                else
                    female[5] = this.state.avgTime[i][4];
            }
            else if (this.state.avgTime[i][2] === 60) {
                if (this.state.avgTime[i][3] === 'M') 
                    male[6] = this.state.avgTime[i][4];
                else
                    female[6] = this.state.avgTime[i][4];
            }
            
        }
        const expData = {
            labels: ["10대 남자","10대 여자","20대 남자","20대 여자","30대 남자","30대 여자","40대 남자","40대 여자","50대 남자","50대 여자","60대 남자","60대 여자"],
            datasets: [
                {
                    data: [male[1].toFixed(1),female[1].toFixed(1),male[2].toFixed(1),female[2].toFixed(1),male[3].toFixed(1),female[3].toFixed(1),male[4].toFixed(1),female[4].toFixed(1),male[5].toFixed(1),female[5].toFixed(1),male[6].toFixed(1),female[6].toFixed(1)],
                    borderWidth:2,
                    hoverBoderWidth:3,
                    backgroundColor: [
                        'rgba(255, 0, 0, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(0, 255, 0, 0.2)',
                        'rgba(112, 112, 123, 0.2)',
                        'rgba(191, 255, 0, 0.2)',
                        'rgba(150, 75, 0, 0.2)',
                        'rgba(255, 105, 180, 0.2)',  
                        'rgba(255, 127, 0, 0.2)',
                        'rgba(0, 255, 255, 0.2)'                  
                    ],
                    borderColor: [
                        'rgba(255,0,0,1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(0,255,0,1)',
                        'rgba(112, 112, 123, 1)',
                        'rgba(191, 255, 0, 1)',
                        'rgba(150, 75, 0, 1)',
                        'rgba(255, 105, 180, 1)',
                        'rgba(255, 127, 0, 1)',
                        'rgba(0, 255, 255, 1)'
                    ],
                    fill: true
                }
            ]
        };
        
        const options = this.state.keywordArray.map( (item, index)=> {
            return <option value={item} key={index}>{item[0]}</option>
        });

        const send =()=> {
            const KEYWORD = this.state.KEYWORD;
            const PRODUCT = this.state.PRODUCT;
         
         fetch("http://localhost:3003/api3", { 
                method: "post", //통신방법
                headers: {
                "content-type": "application/json",
                },
                body: JSON.stringify({
                    keyword : KEYWORD,
                    product : PRODUCT
                })
            })
            .then((res) => {
                return res.json();
            })
            .then((data) => {
                this.setState({
                  avgTime : data.avgTime
                  })
                  
            });
        }

        return (
            <div>
                <Helmet>
                    <title>MONITORING : 광고별 연령대 평균 시청 시간</title>
                </Helmet>
                <div className="option_class">
                    <select id = "option1" onChange = {this.categoryChange}>
                        <option value ={this.state.keywordArray} >선택해주세요</option>
                        {options}
                    </select>

                    <select id = "option2" onChange = {this.productChange}>
                        <option value ={this.state.product_name} >선택해주세요</option>
                    </select>

                    <button onClick = {() => {send()}}>확인</button>
                </div>

                <div className="bar">
                    <Bar
                        options= {{
                            title: {
                                display:true,
                                fontSize:15,
                                text:"광고별 연령대 평균 시청 시간 (초)"
                            },
                            legend: {
                                display:false,
                            },
                            scales : {
                                yAxes:[{
                                    ticks: {
                                        min:0,
                                        max:20,
                                        stepSize:5,
                                    }
                                }]
                            },
                            maintainAspectRatio: true
                        }}
                        
                        data={expData}
                        height={120}
                    /> 
                </div>
            </div>
            
        );
    }
}
export default Ms3;