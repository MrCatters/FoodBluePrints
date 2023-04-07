import React from "react";
import "./login.css";
import axios from 'axios';
import { Link } from "react-router-dom"

function Login(){
    return (
        <div className="Login">
            <h2>Login</h2>
            <Link to = "/home"> home </Link>
            <form className="login-form">
                <label for ="user">Username</label>
                <input type = "text" id = "user"  placeholder="enter username.."/>
                <label for = "password">Password</label>
                <input type = "text" id = "password" placeholder="enter password..."/>
                <button>Log in</button>
            </form>
        </div>
    );
}

export default Login