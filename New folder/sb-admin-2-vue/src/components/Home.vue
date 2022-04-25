<template>
  <div id="wrapper">
    <!-- Navigation -->
    <nav
      class="navbar navbar-default navbar-static-top"
      role="navigation"
      style="margin-bottom: 0"
    >
      <div class="navbar-header">
        <button
          type="button"
          class="navbar-toggle"
          data-toggle="collapse"
          data-target=".navbar-collapse"
        >
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="index.html">Admin site</a>
      </div>
      <!-- /.navbar-header -->

      <ul class="nav navbar-top-links navbar-right">
        <!-- /.dropdown -->
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">
            <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
          </a>
          <ul class="dropdown-menu dropdown-user">
            <li class="divider"></li>
            <li>
              <a href="javascript:void(0)" @click="logout()"
                ><i class="fa fa-sign-out fa-fw"></i> Logout</a
              >
            </li>
          </ul>
          <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
      </ul>
      <!-- /.navbar-top-links -->

      <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
          <ul class="nav" id="side-menu">
            <li>
              <router-link to="/dashborad">
                <i class="fa fa-dashboard fa-fw"></i> Dashboard
              </router-link>
            </li>
            <!-- category link -->
            <li>
              <router-link to="/category"> Danh mục sản phẩm </router-link>
            </li>

            <li>
              <router-link to="/product"> Danh sách sản phẩm </router-link>
            </li>
             <li>
              <router-link to="/user"> Quản lý tài khoản </router-link>
            </li>
          </ul>
        </div>
        <!-- /.sidebar-collapse -->
      </div>
      <!-- /.navbar-static-side -->
    </nav>

    <router-view />
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      msg: "Welcome to Your Vue.js App",
    };
  },
  methods: {
    logout() {
      let loc = window.location;
      const port = loc.port ? ":" + loc.port : "";
      loc.href = `//${loc.hostname}${port}/#/login`;
      sessionStorage.clear();
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
