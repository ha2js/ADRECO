<template>
  <base-table :data="tableData"
              :columns="this.columns"
              thead-classes="text-primary"
              >
  </base-table>
</template>
<script>
  import { BaseTable } from "@/components";
  export default {
    components: {
      BaseTable
    },
    props: {
      tableData: {
        type: Array
      },
    },
    data() {
      return {
        columns : [
          "Rank",
          "Product-Name"
        ]
      }
    },
    computed: {
      table(){
        return this.$t('dashboard.usersTable');
      }
      
    }
  }
</script>
<style>
</style>
