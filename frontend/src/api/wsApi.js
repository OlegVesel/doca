import { Client } from '@stomp/stompjs'

let stompClient = null
let userLogin = localStorage.getItem('login')
let notifications = []

export function connect()  {
    userLogin = localStorage.getItem('login')
    stompClient = new Client({
        brokerURL : "ws://192.168.0.106:7777/ws",
        onConnect : () => {
            stompClient.subscribe(
                `/specific/notification/${userLogin}`, result => {
                    notifications.forEach(notification => notification(JSON.parse(result.body)))
                }
            );
            console.log('subscribeBy', userLogin)
        },
        onStompError : frame => {
            console.log("error in stomp ",  frame)
        },
        onWebSocketError : frame => {
            console.log("error in WebSocket ", frame)
        }
    })

    stompClient.activate()
}

export function addNotification(notification){
    notifications.push(notification)
}

export function clearNotification(){
    notifications = []
}

export function send(order) {
    stompClient.publish({
        destination: '/app/task',
        body: JSON.stringify(order)
    });
}
