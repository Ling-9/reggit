function loginApi(params) {
    return $axios({
      'url': '/user/login',
      'method': 'post',
      data:{...params}
    })
  }

function loginoutApi() {
  return $axios({
    'url': '/user/loginout',
    'method': 'post',
  })
}

  