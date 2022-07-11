import App from '../App'
import Autogen from '../pages/Autogen'
import Homepage from '../pages/Homepage'
import Login from '../pages/Login'
import Dataprocess from '../pages/Dataprocess'
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'

const BaseRouter = () => (
    <Router>
        <Routes>
            <Route path='/' element={<App />}>
                <Route path = '/homepage' element = {<Homepage />}></Route>
                <Route path = '/dataprocess' element = {<Dataprocess />}></Route>
                <Route path = '/autogen' element = {<Autogen />}></Route>
            </Route>
            <Route path = '/login' element = {<Login />}></Route>

        </Routes>
    </Router>
)
export default BaseRouter