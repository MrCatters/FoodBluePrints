import React from "react";
import { useState, useEffect } from "react";
import Header from "./Header";
import RecipeCard from "./RecipeCard";
import axios from "axios"
import "./profile.css"


function Profile() {
    let userEmail;
    let recipes_list;
    let temp = [];
    const [items, setItem] = useState([]);
    const cookiearray = document.cookie.split('=')
    const cookietemp = (cookiearray[1]);
    const cookie = (cookietemp.split(';'))[0] 
    /*
    axios.get("http://127.0.0.1:8080/api/v1/user/user_information",{
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${cookie}`
        }
    })
    .then(function(response) {
        console.log(response)
        userEmail = response.data.email
        console.log(userEmail)
    }).catch(function (error) {
        console.log(error)
    });

    axios.get("http://127.0.0.1:8080/api/v1/recipe/auth_users_recipes",{
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${cookie}`
        }
    })
    .then(function(response) {
      console.log("resposne for auth recipes");
      recipes_list = response.data.recipes;
      recipes_list.forEach(recipe => {
        setItem(items => [...items, <RecipeCard title ={recipe.name} author = {recipe.author} content ={recipe.contents}></RecipeCard>])
    });    

    }).catch(function (error) {
        console.log(error)
    });
*/
    useEffect(() => {
        axios.get("http://127.0.0.1:8080/api/v1/user/user_information",{
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${cookie}`
            }
        })
        .then(function(response) {
            console.log(response)
            userEmail = response.data.email
            console.log(userEmail)
        }).catch(function (error) {
            console.log(error)
        });
    
        axios.get("http://127.0.0.1:8080/api/v1/recipe/auth_users_recipes",{
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${cookie}`
            }
        })
        .then(function(response) {
          console.log("resposne for auth recipes");
          console.log(response);
          recipes_list = response.data.recipes;
          let temp = []
          let id = 0;
          recipes_list.forEach(recipe => {
            temp.push(<RecipeCard key = {id} title ={recipe.name} author = {recipe.author} content ={recipe.contents}></RecipeCard>);
            id++
        });    
            setItem(temp)
        }).catch(function (error) {
            console.log(error)
        });
        
       
    }, []) 

    return (
        
        <div className="profile">
            <Header />
            <div className="container">
                <h1>Welcome back, Joe</h1>
                <h2>Your Recipies</h2>

                {items}
            </div>
           
        </div>
    );
}

export default Profile