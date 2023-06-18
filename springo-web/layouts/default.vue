<template>
  <div>
    <Header/>

    <nuxt />

    <Footer />
  </div>
</template>

<script>
import Header from "@/components/header.vue"
import Footer from "@/components/footer.vue"

export default {

  components: {
    Header,
    Footer
  }
}
</script>


<style>
/* 全局的默认样式 */
*{
  margin:0;
  padding:0;
}

ul,li,ol{
  list-style: none;
}

a{
  color: #333;
  text-decoration: none;
}

em,i{
  font-style: normal;
}

</style>
