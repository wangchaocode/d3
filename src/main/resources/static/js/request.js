//axios普通get请求
//方法名为test,传入数据value
function get(url,params) {
    console.log("请求get")
    var rtnData=null;
    var data={
        //请求方式
        method:'get',
        //后端接口路径
        url:url,
        //注意这里使用的是params,该属性负责把属性名和属性值添加到url后面(以查询字符串的方式)，一般和get配合使用，但也能和post配合使用
        params: params
    }
    console.log("data:\n"+data.toString())
    axios(data).then((response) => {
        //response是一个返回的promise对象，该注释所在的这行一般对该promise对象进行处理从而获取数据
    }).catch((error) => {
        //对error进行处理
    })
}
//方法名为test,传入数据value
function post(url,data) {
    axios({
        //请求方式
        method:'post',
        //后端接口路径
        url:url,
        //注意这里使用的是params,该属性负责把属性名和属性值添加到url后面(以查询字符串的方式)，一般和get配合使用，但也能和post配合使用
        data: data
    }).then((response) => {
        //response是一个返回的promise对象，该注释所在的这行一般对该promise对象进行处理从而获取数据
       return response;
    }).catch((error) => {
        //对error进行处理
    })
}
/*
* 说明: 弹窗提示,3秒后自动消失
* 调用: alert_tips('操作成功','success');
* */
function alert_tips(str,status) {
    let info_css = 'display: inline-block;position: fixed;z-index: 888;top: 0;right: 30px;text-align: center;padding: 10px 20px;border-radius: 5px;color: #31708f;background-color: #d9edf7;border-color: #bce8f1';
    let danger_css = 'display: inline-block;position: fixed;z-index: 888;top: 0;right: 30px;text-align: center;padding: 10px 20px;border-radius: 5px;color: #a94442;background-color: #f2dede;border-color: #ebccd1;'
    let success_css = 'display: inline-block;position: fixed;top: 0;right: 30px;text-align: center;padding: 10px 20px;border-radius: 5px;color: #3c763d;background-color: #dff0d8;border-color: #d6e9c6;'
    let warning_css = 'display: inline-block;position: fixed;z-index: 888;top: 0;right: 30px;text-align: center;padding: 10px 20px;border-radius: 5px;color: #8a6d3b;background-color: #fcf8e3;border-color: #faebcc;'
    let add_alert = '';
    if(status === 'success'){
        add_alert = '<span id="alert_test" style="'+success_css+'">'+str+'</span>';
    }else if(status === 'info'){
        add_alert = '<span id="alert_test" style="'+info_css+'">'+str+'</span>';
    }else if(status === 'danger'){
        add_alert = '<span id="alert_test" style="'+danger_css+'">'+str+'</span>';
    }else if(status === 'warning'){
        add_alert = '<span id="alert_test" style="'+warning_css+'">'+str+'</span>';
    }
    $('body').append(add_alert);
    //transition: all 3s 2s linear;
    $('span#alert_test').css({"top":"30px","transition":"3s"});
    setTimeout(() => {
        // console.log('sleep 1.8s.');
        let index = 10;
        let interval = setInterval(function () {
            if(index === -60){ //设3秒时间段
                clearInterval(interval);
                $('#alert_test').remove(); //显示完后删除节点
            }
            $('span#alert_test').css({"opacity":(index/10)});
            // console.log('===>',index);
            index--;
        },30)
    }, 1000); //延时1.8秒
}
