import React from 'react';
import {Helmet} from 'react-helmet';

class Ms1 extends React.Component{    
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
          visibility:'hidden',
          KEYWORD:'',
          PRODUCT:'',
          allcount:[[]],
          seecount:[[]],
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
        const options = this.state.keywordArray.map( (item, index)=> {
            return <option value={item} key={index}>{item[0]}</option>
        });

        const send =()=> {
            const KEYWORD = this.state.KEYWORD;
            const PRODUCT = this.state.PRODUCT;
         
         fetch("http://localhost:3003/api1", { 
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
                    allcount : data.allcount,
                    seecount : data.seecount
                })
                console.log(this.state.allcount);
                console.log(this.state.seecount);
                if (this.state.allcount.length === 0) {
                    this.setState(
                        {
                            visibility:'visible'
                        }
                    );
                }
                else if (this.state.allcount.length > 0){
                    this.setState(
                        {
                            visibility:'hidden'
                        }
                    );
                }
            });
        }
        
        /*
        const select = this.state.allcount.map((item,index) => {
            var percent = 0;
            var Gender = "남성";
            if (item[3] === "F")
                Gender = "여성";
            if(item.length === 0)   {
                return;
            }

            var allcount_idx = 0;
            
            
            if ((this.state.seecount[allcount_idx] == undefined) || (this.state.seecount[allcount_idx][2] !== item[2]) || (this.state.seecount[allcount_idx][3] !== item[3]) ) {
                percent = 0;
            }
            else {
                var percent = this.state.seecount[allcount_idx][4] / item[4] * 100;
                allcount_idx++;
            }
            percent.toFixed(1);
            return <tbody key={index}>
                <tr>
                    <th>{item[0]}</th>
                    <th>{item[1]}</th>
                    <td>{item[2]}대</td>
                    <td>{Gender}</td>
                    <td>{percent}%</td>
                </tr>
            </tbody>

        })            
*/
        return (
            <div>
                <Helmet>
                    <title>MONITORING : 제품 관심도</title>
                </Helmet>
                <div className="option_class">
                    <select id = "option1" onChange = {this.categoryChange}>
                        <option value ={this.state.keywordArray} >선택해 주세요</option>
                        {options}
                    </select>

                    <select id = "option2" onChange = {this.productChange}>
                        <option value ={this.state.product_name} >선택해 주세요</option>
                    </select>
                    <button onClick = {() => {send()}}>확인</button>
                </div>
                <div>
                    <table className = "ms_table">
                        <thead>
                            <tr>
                                <th>카테고리</th>
                                <th>상품명</th>
                                <th>연령</th>
                                <th>성별</th>
                                <th>일치율</th>
                            </tr>
                        </thead>

                        <tbody>
                            <tr>
                                <th>남성캐주얼</th>
                                <th>이중직 원단 기모 데님팬츠 2종 택1</th>
                                <td>10대</td>
                                <td>남성</td>
                                <td>60%</td>
                            </tr>
                            <tr>
                                <th>남성캐주얼</th>
                                <th>이중직 원단 기모 데님팬츠 2종 택1</th>
                                <td>20대</td>
                                <td>남성</td>
                                <td>100%</td>
                            </tr>
                            <tr>
                                <th>남성캐주얼</th>
                                <th>이중직 원단 기모 데님팬츠 2종 택1</th>
                                <td>30대</td>
                                <td>남성</td>
                                <td>66.7%</td>
                            </tr>
                        </tbody>

                        
                        <tbody style={{visibility:this.state.visibility}}>
                            <tr>
                                <th colSpan={5} style={
                                    {width:'750px', textAlign:'center'}
                                    }>등록된 정보가 없습니다.</th>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}
export default Ms1;