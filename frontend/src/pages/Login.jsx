import React from 'react';
import { Button, message, Form, Input } from 'antd';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import {useNavigate} from 'react-router-dom'
import "./less/login.less"
import logoImg from '../assets/logo.png'
import {LoginApi} from "../request/api";

export default function Login() {
    const navigate = useNavigate()
    const onFinish = (values) => {
        LoginApi({
            username: values.username,
            password: values.password
        }).then(res=>{
            if(res.status === 200) {
                message.success(res.msg);
                localStorage.setItem("token",res.data.token)
                localStorage.setItem("username",res.data.user.username)
                localStorage.setItem("usertype",res.data.user.usertype)
                setTimeout(()=>{
                    navigate('/homepage')
                },1500)
            }else{
                message.error(res.msg);
            }
        })
    };


    return (
        <div className="login">
        <div className="login_box">
            <img src={logoImg} alt=""/>
            <Form className="formbox"
                name="basic"
                initialValues={{
                    remember: true,
                }}
                onFinish={onFinish}
                autoComplete="off"
            >
                <Form.Item
                    label="Username"
                    name="username"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your username!',
                        },
                    ]}
                >
                    <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Enter your username"/>
                </Form.Item>

                <Form.Item
                    label="Password"
                    name="password"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your password!',
                        },
                    ]}
                >
                    <Input.Password  prefix={<LockOutlined className="site-form-item-icon" />} placeholder="Enter your password"/>
                </Form.Item>


                <Form.Item

                >
                    <Button type="primary" htmlType="submit" block>
                        Login
                    </Button>
                </Form.Item>
            </Form>
        </div>
        </div>
    );
}
