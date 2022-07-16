import { Menu } from 'antd';
import React, {useState, useEffect} from 'react';
import {useNavigate, useLocation} from 'react-router-dom'

export default function Aside() {
    const navigate = useNavigate();
    const location = useLocation();
    const [defaultKey, setDefaultKey] = useState('')
    useEffect(() => {
        let path = location.pathname
        let key = path.split('/')[1]
        setDefaultKey(key)
    }, []);
    
    const onClick = e => {
        navigate('/' + e.key);
        setDefaultKey(e.key)
    };
    return(
        <Menu onClick={onClick}
              style={{
                  width: 190,
              }}
              selectedKeys={[defaultKey]}
              mode="inline"
              theme="dark"
              className="aside"
        >
            <Menu.Item key="homepage">Home</Menu.Item>
            <Menu.Item key="dataprocess">Data Process</Menu.Item>
            <Menu.Item key="autogen">PowerPoint Generator</Menu.Item>

            </ Menu>
    )
}