package com.example.firebasechat

class User {

    var name: String? = null
    var email: String? = null
    var userid: String? = null

    constructor(){}

    constructor(name: String?, email: String?, userid: String?){
        this.name = name
        this.email = email
        this.userid = userid
    }
}