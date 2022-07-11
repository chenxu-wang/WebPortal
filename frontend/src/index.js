import ReactDOM from 'react-dom'
import Router from './router'
import {store} from './store'
import {Provider} from 'react-redux'

ReactDOM.render(
    <Router />
    ,
    document.getElementById('root')
)