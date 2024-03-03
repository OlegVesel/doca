import { Client } from '@stomp/stompjs'

let stompClient = null
let userLogin = localStorage.getItem('login')
let notifications = []

export function connect()  {
    console.log('start webs')
    console.log('user', userLogin)
    stompClient = new Client({
        brokerURL : "ws://localhost:7777/ws",
        onConnect : () => {
            if (userLogin == null)
                userLogin = localStorage.getItem('login')
            stompClient.subscribe(
                `/specific/notification/${userLogin}`, result => {
                    notifications.forEach(notification => notification(JSON.parse(result.body)))
                }
            );

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
