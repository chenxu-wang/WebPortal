import React from 'react'
import {connect} from 'react-redux'
import { Button } from 'antd';
import "./assets/base.css"
import {Outlet} from 'react-router'
export default function App(){
    return (
        <div>
            <Button type="primary">Primary Button</Button>
            <Outlet />
        </div>
    )
}