import React, { useState } from "react";
import "./login.css";
import axios from 'axios';
import { Link } from "react-router-dom"
import { useSignIn } from "react-auth-kit";

function Login(props){

    const [u_pass,setPass] = useState();
    const [u_email, setEmail] = useState();

    const signIn = useSignIn();

    const handleSubmit = (e) => {
        let values = {u_email, u_pass}
        e.preventDefault();
        axios.post("http://127.0.0.1:8080/api/v1/auth/authenticate", {
            "email": u_email,
            "password": u_pass,
        })
        .then(function (response) {
            
            console.log(response)
            //signIn({
                //response.
            //});
        })
        .catch(function(error) {
            console.log(error)
        });

    }
    return (
        <div className="Login">
            <h2>Login</h2>
            <Link to = "/home"> home </Link>
            <form onSubmit = {handleSubmit} className="login-form">
              
                <input type = "text" onChange = {(e) => setEmail(e.target.value)}id = "user"  placeholder="enter username.."/>
             
                <input type = "text" id = "password" onChange = {(e) => setPass(e.target.value)} placeholder="enter password..."/>

                <input type = "submit" value = "Submit"/>
            </form>
        </div>
    );
}

export default Login;