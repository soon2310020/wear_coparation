<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      class="login-form"
      autocomplete="on"
      label-position="left"
    >
      <div class="title-container">
        <h3 class="title">Login Form</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="Username"
          name="username"
          type="text"
          tabindex="1"
          autocomplete="on"
        />
      </el-form-item>

      <el-tooltip
        v-model="capsTooltip"
        content="Caps lock is On"
        placement="right"
        manual
      >
        <el-form-item prop="password">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
          <el-input
            :key="passwordType"
            ref="password"
            v-model="loginForm.password"
            :type="passwordType"
            placeholder="Password"
            name="password"
            tabindex="2"
            autocomplete="on"
            @keyup="checkCapslock"
            @blur="capsTooltip = false"
            @keyup.enter="handleLogin"
          />
          <span class="show-pwd" @click="showPwd">
            <!-- <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" /> -->
          </span>
        </el-form-item>
      </el-tooltip>

      <el-button
        :loading="loading"
        type="primary"
        style="width: 100%; margin-bottom: 30px"
        @click.prevent="handleLogin"
        >Đăng nhập</el-button
      >
    </el-form>

    <el-dialog title="Or connect with" :visible="showDialog">
      Can not be simulated on local, so please combine you own business
      simulation! ! !
      <br />
      <br />
      <br />
      <social-sign />
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        username: null,
        password: null,
      },
      //   loginRules: {
      //     username: [{ required: true, trigger: 'blur', validator: validateUsername }],
      //     password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      //   },
      passwordType: "password",
      capsTooltip: false,
      loading: false,
      showDialog: false,
      redirect: undefined,
      otherQuery: {},
      msg: "Welcome to Your Vue.js App",
      baseURL: "http://192.168.1.208:9999/wear_shop/api/",
    };
  },
  methods: {
    checkCapslock(e) {
      const { key } = e;
      this.capsTooltip = key && key.length === 1 && key >= "A" && key <= "Z";
    },
    showPwd() {
      if (this.passwordType === "password") {
        this.passwordType = "";
      } else {
        this.passwordType = "password";
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    handleLogin() {
      console.log("login");
      if (this.loginForm.username == null || this.loginForm.username == "") {
        this.$toast.error("Không được để trống tài khoản hoặc mật khẩu !");
        return;
      }
      if (this.loginForm.password == null || this.loginForm.password == "") {
        this.$toast.error("Không được để trống tài khoản hoặc mật khẩu !");
        return;
      }
      axios
        .post(this.baseURL + `authenticate`, this.loginForm)
        .then((response) => {
          window.sessionStorage.setItem(
            "user",
            "Bearer " + response.data.token
          );
          console.log(sessionStorage.getItem("user"));
          this.$toast.open("Đăng nhập thành công !");
          axios.create({
            headers : {
                Authorization: "Bearer " + response.data.token,
                ...axios.defaults.headers,
            }
          });
          // this.$toastr.zs('SUCCESS MESSAGE', 'Thêm mới thành công !')
          let loc = window.location;
          const port = loc.port ? ":" + loc.port : "";
          loc.href = `//${loc.hostname}${port}/#/dashborad`;
          // this.dialogFormVisible = !this.dialogFormVisibles
        })
        .catch((e) => {
          this.$toast.error("Đăng nhập thất bại !");
          // this.errors.push(e);
        });
    },
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
