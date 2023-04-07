import React, { useState } from "react";
import "./register.css"
import { Link } from "react-router-dom" 
import axios from "axios";


function Register() {

    const [u_pass,setPass] = useState();
    const [u_email, setEmail] = useState();
    const [u_pass2, setPass2] = useState();
    const [matchstate,setMatchstate] = useState();

    const handleSubmit = (e) => {
        e.preventDefault();

        if(u_pass !== u_pass2) {
            //alert("passwords do not match!")
            setMatchstate("passwords do not match!");
            
        } else {
            setMatchstate();
            axios.post()
        }
    }
    return (
        <div className="Register">
            <h1>FoodBlueprints</h1>
            <h2>Please register for an account</h2>
            <div className="register-form-container">
                <form onSubmit = {handleSubmit} className="login-form">
                    <input type = "text" required = "true" onChange = {(e) => setEmail(e.target.value)}id = "f_name"  placeholder="first name.."/>
                    <input type = "text" required = "true" onChange = {(e) => setEmail(e.target.value)}id = "l_name"  placeholder="last name.."/>   
                    <input type = "text" required = "true" onChange = {(e) => setEmail(e.target.value)}id = "user"  placeholder="enter email.."/>
                    <input type = "text" required = "true"id = "password" onChange = {(e) => setPass(e.target.value)} placeholder="enter password..."/>
                    <input type = "text" required = "true"id = "password1" onChange = {(e) => setPass2(e.target.value)} placeholder="enter password again..."/>

                    <input type = "submit" value = "Submit"/>
                </form>
                <p>{matchstate}</p>
                <Link to = "/login">Already have an account? Log in</Link>
            </div>
        </div>
    );
}


export default Register