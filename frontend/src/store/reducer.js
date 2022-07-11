// define
const defaultState = {
    msg: "hello"
}

// export
export default (state=defaultState, action) => {
    let newState = JSON.parse(JSON.stringify(state))
    switch(action.type){
        case "changeMsgFn": newState.msg = action.value;
        break;
        default:
            break;
    }
    return newState
}