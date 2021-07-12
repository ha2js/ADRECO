<template>
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
</template>

<script>
    export default {
        
    }
</script>

<style lang="scss" scoped>
.middle-box {
    max-width: 590px;
    margin: 0 auto;
    padding-top: 40px;
}

.info {
    text-align: center;
}
</style>