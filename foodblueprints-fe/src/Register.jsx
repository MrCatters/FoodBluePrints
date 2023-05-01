import React, { useState } from "react";
import "./register.css"
import { Link, useNavigate } from "react-router-dom" 
import axios from "axios";
import { useSignIn } from "react-auth-kit";


function Register() {
    const [f_name,setF_name] = useState();
    const [l_name,setL_name] = useState();
    const [u_pass,setPass] = useState();
    const [u_email, setEmail] = useState();
    const [u_pass2, setPass2] = useState();
    const [matchstate,setMatchstate] = useState();
    const signIn = useSignIn();

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
                    
                    navigate("/home");    
           
                
            })
            .catch(function error(error) {
                if (error.response.status === 400) {
                    setMatchstate("This email is already associated with an account");
                }
                console.log(error);
            });
        }
    }
    return (
        <div className="Register">
            
            <div className="register-form-container">
            <h1>FoodBlueprints</h1>
            <h2>Please sign up for an account</h2>
                <form onSubmit = {handleSubmit} className="login-form">
                    <input type = "text" required  onChange = {(e) => setF_name(e.target.value)}id = "f_name"  placeholder="first name.."/>
                    <input type = "text" required  onChange = {(e) => setL_name(e.target.value)}id = "l_name"  placeholder="last name.."/>   
                    <input type = "text" required  onChange = {(e) => setEmail(e.target.value)}id = "user"  placeholder="enter email.."/>
                    <input type = "password" required id = "password" onChange = {(e) => setPass(e.target.value)} placeholder="enter password..."/>
                    <input type = "password" required id = "password1" onChange = {(e) => setPass2(e.target.value)} placeholder="enter password again..."/>

                    <input type = "submit" value = "Sign up"/>
                </form>
                <p>{matchstate}</p>
                <Link to = "/login">Already have an account? Log in</Link>
            </div>
        </div>
    );
}


export default Register