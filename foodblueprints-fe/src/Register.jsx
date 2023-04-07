import React, { useState } from "react";
import "./register.css"
import { Link, useNavigate } from "react-router-dom" 
import axios from "axios";


function Register() {
    const [f_name,setF_name] = useState();
    const [l_name,setL_name] = useState();
    const [u_pass,setPass] = useState();
    const [u_email, setEmail] = useState();
    const [u_pass2, setPass2] = useState();
    const [matchstate,setMatchstate] = useState();

    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();

        if(u_pass !== u_pass2) {
            //alert("passwords do not match!")
            setMatchstate("passwords do not match!");
            
        } else {
            setMatchstate();
            axios.post("http://127.0.0.1:8080/api/v1/auth/register",{
                "firstname":f_name,
                "lastname":l_name,
                "email":u_email,
                "password":u_pass
                
             })
            .then(function response(response) {
            
                if (null ){ 
                    //success case
                    navigate("/home");    
                } else if (null){ 
                    //already registered
                    setMatchstate("This email is already associated with an account")
                } else {
                    //something else
                    
                }
                
            })
            .catch(function error(error) {
                console.log(error);
            });
        }
    }
    return (
        <div className="Register">
            <h1>FoodBlueprints</h1>
            <h2>Please register for an account</h2>
            <div className="register-form-container">
                <form onSubmit = {handleSubmit} className="login-form">
                    <input type = "text" required = "true" onChange = {(e) => setF_name(e.target.value)}id = "f_name"  placeholder="first name.."/>
                    <input type = "text" required = "true" onChange = {(e) => setL_name(e.target.value)}id = "l_name"  placeholder="last name.."/>   
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