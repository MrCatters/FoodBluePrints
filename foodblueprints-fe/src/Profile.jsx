import React from "react";
import { useState, useEffect, useLayoutEffect} from "react";
import Header from "./Header";
import RecipeCard from "./RecipeCard";
import axios from "axios"
import "./profile.css"


function Profile() {
    let userEmail;
    let recipes_list;
    let temp = [];
    const [switcher, setSwitcher] = useState(false);
    const [ping, setPing] = useState(false);
    const [name,setName] = useState();
    const [items, setItem] = useState();
    const [favoriteIDs, setFavoriteIDs] = useState([]);
    const cookiearray = document.cookie.split('=')
    const cookietemp = (cookiearray[1]);
    const cookie = (cookietemp.split(';'))[0];
    
    useEffect(() => {
        setTimeout(function() {
            setPing(true);
        },700)
    },[])
    useEffect(() => {
        if (switcher == false) {

        axios.get("http://127.0.0.1:8080/api/v1/user/user_information",{
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${cookie}`
            }
        })
        .then(function(response) {
            console.log(response)
            setName(response.data.firstName);
           
        }).catch(function (error) {
            console.log(error)
        });
        
        axios.get('http://127.0.0.1:8080/api/v1/recipe/favorited_recipes', {
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${cookie}`
            }
        })
        .then(function (response) {
            let tempIDs = [];
            recipes_list = response.data.recipes;
            recipes_list.forEach(recipe => {
                tempIDs.push(recipe.id);
            });
            setFavoriteIDs(tempIDs);

        })
        .then(function (error) {
            console.log(error);
        });

        axios.get("http://127.0.0.1:8080/api/v1/recipe/auth_users_recipes",{
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${cookie}`
            }
        })
        .then(function(response) {
          setItem([]);
          console.log("resposne for auth recipes");
          console.log(response);
          console.log('------------------');
          recipes_list = response.data.recipes;
          let temp = []
          
          response.data.recipes.forEach(recipe => {
            temp.push(<RecipeCard id = {recipe.id}key = {recipe.id} title ={recipe.name} author = {recipe.user.firstName} content ={recipe.contents} img = {recipe.image} favorites = {favoriteIDs}></RecipeCard>);
        })  
            setItem(temp)
        }).catch(function (error) {
            console.log(error)
        });
        
    } else {
        
        axios.get('http://127.0.0.1:8080/api/v1/recipe/favorited_recipes', {
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${cookie}`
            }
        })
        .then(function (response) {
            console.log('response for favorite recipies');
            console.log(response);
            console.log('------------------');
            if (response.data.recipes.length > 0) {
                setItem([])
                recipes_list = response.data.recipes;
                let temp = []
                response.data.recipes.forEach(recipe => {
                    temp.push(<RecipeCard id = {recipe.id}key = {recipe.id} title ={recipe.name} author = {recipe.user.firstName} content ={recipe.contents} img = {recipe.image} favorites = {favoriteIDs}></RecipeCard>);
                })    
                  setItem(temp)    
            }
            
            console.log(response)
        })
        .then(function (error) {

        });

    }
    }, [switcher,ping]) 

    return (
        
        <div className="profile">
            <Header searchActive = {false}/>
            <div className="container">
                <div className="profile-header-container">
                    <h1>Welcome back, {name}</h1>
                    <h2>Your Recipies</h2>    
                </div>
                <div className="options">
                    <button onClick={e => setSwitcher(false)}>Your Recipes</button>
                    <button onClick={e => setSwitcher(true)}>Favorite Recipes</button>
                </div>
                {items}
            </div>
           
        </div>
    );
}

export default Profile