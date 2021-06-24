import React from "react";
import './css/ads_view.css';


export default class Ads_view extends React.Component {
  constructor(props) {
    super(props);
 
    setInterval(() => {
      fetch("http://localhost:3001/check1", { 
        method: "post", //통신방법
        headers: {
          "content-type": "application/json",
        },
        body: JSON.stringify(),
      })
      .then((res) => res.json())
      .then((json) => {
        this.setState({
          check_req:json.check_req
        })
      });

      console.log(this.state.check_req);
      this.state.check_req===true && fetch("http://localhost:3001/Ads_img1", { 
        method: "post", //통신방법
        headers: {
          "content-type": "application/json",
        },
        body: JSON.stringify(),
      })
      .then((res) => res.json())
      .then((json) => {
        this.setState({
          prod_name:json.prod_name,
          prod_url: json.prod_url,
          prod_brand: json.prod_brand,
          prod_price: json.prod_price,
        })
      });
    }, 1000);
    
    this.state = {
      prod_name:[],
      prod_url:[],
      prod_brand:[],
      prod_price:[],
      check_req:false
    };

  }

  render() {
    return (
      <div className="app-body">
        <div className="contents">
            <div className="header"><img className="shin_logo" src="images/shinsegae.png" alt = "로고"></img></div>
            <img className="ads_img" src= {this.state.prod_url} alt="상품"></img>
            <div className="adstxt_body">
                <div className="ads_txt1"><span className="brand_name">&nbsp;{this.state.prod_brand}&nbsp;</span></div>
                <div className="ads_txt2"> {this.state.prod_name} </div>
                <div className="ads_txt3">{this.state.prod_price}&nbsp;￦</div>
            </div>
            {/* <div className="ads_footer">
                <div className="footer_title">(주)에스에스지닷컴
                    <span className="QR">모바일 앱으로 만나보세요!<br></br> 
                        <img className="QR_img" src="images/shinsegae_QR.png" alt = "QR코드"></img>
                    </span>
                </div>
                <div>
                    <div>
                        <img className ="consultant" src="images/consultant.png" alt = "상담"></img>
                    </div> <span className="tel">1577-3419</span>
                </div>
                <div>
                    <span>대표자: 최우정</span>
                    <span>서울특별시 종로구 우정국로 26</span>
                    <span>사업자등록번호: 870-88-01143</span>
                    <span>통신판매업 신고번호: 제2020-서울종로-1154호</span>
                </div>
                <div>
                    <span>개인정보보호책임자: 김낙호</span>
                    <span>소비자피해보상보험</span>
                    <span>SGI 서울보증</span>
                    <span>Fax: 02-2068-7118</span>
                    <span>ssg@ssg.com</span>
                </div>
            </div> */}
        </div>
    </div>
    );
  }
}