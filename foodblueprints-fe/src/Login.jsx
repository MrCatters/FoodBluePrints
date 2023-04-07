import React from "react";
import "./login.css";
import axios from 'axios';
import { Link } from "react-router-dom"

function Login(){
    return (
        <div className="Return">
            <h2>Login</h2>
            <Link to = "/home"> home </Link>
        </div>
    );
}

export default Login