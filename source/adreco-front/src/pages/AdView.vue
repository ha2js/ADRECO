<template>
    <div class="box">
        <div class="middle-box">
            <h1>ADRECO</h1>
            <a target="_blank">
                <img
                class="object-fit"
                :src="this.productUrl"
                width="100%"
                height="400"
                />
            </a>
            <div class="info">
                <h3>{{this.productBrand}}</h3>
                <h3>{{this.productName}}</h3>
                <h4>{{this.productPrice}}원</h4>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        created() {
            this.changeAdview();
            setInterval(this.changeAdview, 2000);
        },
        data() {
            return {
                productBrand: "",
                productName: "",
                productPrice: "",
                productUrl: ""
            }
        },
        methods: {
            async changeAdview() {
                await axios.get("/api/adView/getAdView")
                        .then((res) => {
                            const response = res.data.data;
                            this.productBrand = response.productBrand;
                            this.productName = response.productName;
                            this.productPrice = response.productPrice;
                            this.productUrl = response.url;
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