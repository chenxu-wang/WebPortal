import React from 'react'
import "./assets/base.less"
import {Outlet} from 'react-router'
import { Layout } from 'antd';
import Header from './components/Header'
import Aside from "./components/Aside";


//main layout
export default function App(){
    return (
        <Layout id="app" >
            <Header />
            <div className='container'>
                <Aside />
                <div className='container_box'>
                    <Outlet />
                </div>
            </div>
            <footer>Footer</footer>
        </Layout>

    )
}