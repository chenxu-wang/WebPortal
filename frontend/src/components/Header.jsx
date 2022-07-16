import logoImg from "../assets/logo-white.png";
import { DownOutlined, SmileOutlined } from '@ant-design/icons';
import { Dropdown, Menu, Space, message } from 'antd';
import React, {useEffect, useState} from "react";
import {useNavigate} from 'react-router-dom'


export default function Header(){
    const navigate = useNavigate();
    const [username, setUsername] = useState("default")
    //componentDidMount
    useEffect(()=>{
        let username1 = localStorage.getItem('username')
        if(username1){
           setUsername(username1)
        }
    })
    //logout
    const logout = () =>{
        localStorage.clear()
        navigate('/login')
        message.success("Exited")
    }
    const menu = (
        <Menu>
            <Menu.Item key={1}>Edit Profile</Menu.Item>
            <Menu.Divider />
            <Menu.Item danger="true"key={2} onClick={logout}>Exit System</Menu.Item>
        </Menu>
    );
    return(
        <header>
            <a href="http://abbvie.com"> <img href="abbvie.com" src={logoImg} alt=""/></a>
            <div className="right">
                <Dropdown overlay={menu}>
                    <a onClick={e => e.preventDefault()}>
                        <Space>
                            <span>{username}</span>
                            <DownOutlined />
                        </Space>
                    </a>
                </Dropdown>
            </div>
        </header>
    )
}
