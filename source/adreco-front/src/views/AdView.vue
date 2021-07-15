<template>
    <div class="box">
        <div class="middle-box">
            <h1>ADRECO</h1>
            <a href="http://emart.ssg.com/item/itemView.ssg?itemId=1000017850945&siteNo=6001&salestrNo=2034" target="_blank">
                <img
                class="object-fit"
                :src="require('@assets/imgs/tomato.jpg')"
                width="100%"
                height="400"
                />
            </a>
            <div class="info">
                <h3>이마트몰</h3>
                <h3>[국내산] 대추방울토마토 1kg/팩</h3>
                <h4><strike>6,900원</strike> 5,900원</h4>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        created() {
            //setInterval(this.changeAdview, 2000);
        },
        data() {
            return {

            }
        },
        methods: {
            async changeAdview() {
                await axios.get("/api/productData/getAdView")
                        .then((res) => {
                            console.log(res);
                        })
                        .catch((error) => {
                            console.log(error);
                        })
            }
        }
    }
</script>

<style lang="scss" scoped>

html,body {
    margin:0;
    padding:0;
}
.box { background-color:rgb(0,0,0); }
.middle-box {
    background-color:rgb(255,255,255);
    max-width: 590px;
    margin: 0 auto;
    padding:50px 40px 40px 40px;
    min-height:89vh;
}
.info {
    text-align: center;
    
}
</style>