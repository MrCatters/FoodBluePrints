import React, { useState } from "react";
import "./login.css";
import axios from 'axios';
import { Link, useNavigate } from "react-router-dom"
import { useSignIn, useSignOut } from "react-auth-kit";

function Login(props){

    const [u_pass,setPass] = useState();
    const [u_email, setEmail] = useState();
    const [passvalid,setPassvalid] = useState();
    const navigate = useNavigate();
    const signIn = useSignIn();
    const signOut = useSignOut();

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post("http://127.0.0.1:8080/api/v1/auth/authenticate", {
            "email": u_email,
            "password": u_pass,
        })
        .then(function (response) {
            console.log(response)
            
            if (signIn(
                {
                token: response.data.access_token,
                expiresIn: 3600,
                tokenType: "Bearer",
                authState: { email: u_email},
                refreshToken: response.data.refresh_token
                }
            )) {
                setTimeout(() => {
                    navigate("/home")
                }, 100)    
            } else {

            };

            
            
        })
        .catch(function(error) {
            console.log(error)
            if (error.response.status === 401) {
                setPassvalid("Incorrect password or email")
            }

        });

    }
    return (
        <div className="Login">
           
            <div className="login-form-container">
            <h1>FoodBlueprints</h1>
            <h2>Welcome, please log in or sign up</h2>
                <form onSubmit = {handleSubmit} className="login-form">
                    <input type = "text" required onChange = {(e) => setEmail(e.target.value)}id = "user"  placeholder="enter username.."/>
                    <input type = "password" required id = "password" onChange = {(e) => setPass(e.target.value)} placeholder="enter password..."/>

                    <input type = "submit" value = "Sign in"/>
                </form>
                <p>{passvalid}</p>
                <Link to = "/register">Don't have an account? Sign up</Link>
                
            </div>
           
        </div>
    );
}

export default Login;