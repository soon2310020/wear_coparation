<template>

  <div class="login-container">
    <div class="row">
      <div class="col-md-4"></div>
      <div class="col-md-4">
        <div class="title-container">
          <h3 class="title">Đăng ký</h3>
        </div>

        <el-form :model="userCreate">
          <el-form-item label="Username:">
            <el-input
              v-model="userCreate.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="Tên :" >
            <el-input
              v-model="userCreate.firstName"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="Họ :" >
            <el-input
              v-model="userCreate.lastName"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Password :"
            prop="pass"
          >
            <el-input
              type="password"
              v-model="userCreate.password"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Nhập lại Password :"
            prop="pass"
          >
            <el-input
              type="password"
              v-model="userCreate.repassword"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item
            prop="email"
            label="Email"
            :rules="[
              {
                type: 'email',
                message: 'Hãy nhập email của bạn',
                trigger: ['blur', 'change'],
              },
            ]"
          >
            <el-input v-model="userCreate.email"></el-input>
          </el-form-item>

          <div class="row">
            <div class="col-md-6">
              <el-button type="primary" style="width: 100%; margin-bottom: 30px" @click="login()"
                >Đăng nhập</el-button
              >
            </div>
            <div class="col-md-6">
              <el-button
                type="info"
                style="width: 100%; margin-bottom: 30px"
                @click="createUser()"
                >Đăng ký</el-button
              >
            </div>
          </div>
        </el-form>
      </div>
      <div class="col-md-4"></div>
    </div>

    <!-- <el-dialog title="Or connect with" :visible="showDialog">
      Can not be simulated on local, so please combine you own business
      simulation! ! !
      <br />
      <br />
      <br />
      <social-sign />
    </el-dialog> -->
  </div>
</template>

<script>
import axios from "axios";
export default {
  created() {
    this.checkLogined();
  },
  data() {
    return {
      userCreate: {
        id: null,
        username: null,
        password: null,
        repassword: null,
        firstName: null,
        lastName: null,
        email: null,
        role: null,
      },

      // baseURL: "http://192.168.1.208:9999/wear_shop/api/",
      baseURL: "http://localhost:9999/wear_shop/api/",
    };
  },
  methods: {
    createUser() {
      const usernameRegex = new RegExp("^[a-z0-9A-Z]{1,}$");
      if (this.userCreate.username === "" || this.userCreate.username == null) {
        this.$toastr.e("username không được để trống!");
        return;
      }
      if (!usernameRegex.test(this.userCreate.username)) {
        this.$toastr.e("username Không được chứa ký tự đặc biệt!");
        return;
      }

      if (this.userCreate.password === "" || this.userCreate.password == null) {
        this.$toastr.e("password không được để trống!");
        return;
      }
      if (this.userCreate.password !== this.userCreate.repassword) {
        this.$toastr.e("password nhập lại không giống password!");
        return;
      }

      axios
        .post(this.baseURL + `register`, this.userCreate)
        .then((response) => {
              debugger
          this.$toastr.i("Đăng ký thành công !");
          // window.history.go()
           let loc = window.location;
          const port = loc.port ? ":" + loc.port : "";
          loc.href = `//${loc.hostname}${port}/login`;
          // // this.dialogFormVisible = !this.dialogFormVisibles
        }),
        (error) => {
          this.$toastr.e("Username đã tồn tại vui lòng chọn username khác!");
        };
    },
      checkLogined(){

    if (localStorage.getItem("user")) {
      let loc = window.location;
      const port = loc.port ? ":" + loc.port : "";
      loc.href = `//${loc.hostname}${port}`;
    }
  },
  login()
  {
       let loc = window.location;
          const port = loc.port ? ":" + loc.port : "";
          loc.href = `//${loc.hostname}${port}/login`;
  }
  },

};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.login-container {
  height: 100vh;
  min-height: 100%;
  width: 100%;
  background-color: #2d3a4b;
  overflow: hidden;
}
.login-container .login-form {
  position: relative;
  width: 520px;
  max-width: 100%;
  padding: 160px 35px 0;
  margin: 0 auto;
  overflow: hidden;
}

.login-container .tips {
  font-size: 14px;
  color: #fff;
  margin-bottom: 10px;
}
/* .login-container .tips span {
  first-of-type {
    margin-right: 16px;
  }
} */

.svg-container {
  padding: 6px 5px 6px 15px;
  color: #889aa4;
  vertical-align: middle;
  width: 30px;
  display: inline-block;
}

.title-container {
  position: relative;
}
.title-container .title {
  font-size: 26px;
  color: #eee;
  margin: 0px auto 40px auto;
  text-align: center;
  font-weight: bold;
}

.show-pwd {
  position: absolute;
  right: 10px;
  top: 7px;
  font-size: 16px;
  color: #889aa4;
  cursor: pointer;
  user-select: none;
}

.thirdparty-button {
  position: absolute;
  right: 0;
  bottom: 6px;
}

@media only screen and (max-width: 470px) {
  .thirdparty-button {
    display: none;
  }
}
</style>
