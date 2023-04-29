import React from "react";
import Header from "./Header"
import AddPopup from "./AddPopup";
import axios from "axios";
import { useState, useEffect } from "react";
import "./home.css"

function Home(){
    
   

    useEffect(() => {
        
        const cookiearray = document.cookie.split('=')
        const cookietemp = (cookiearray[1]);
        const cookie = (cookietemp.split(';'))[0] 
        
        let data = JSON.stringify({
            "searchString": 10
          });
          
          let config = {
            method: 'post',
            maxBodyLength: Infinity,
            url: 'http://127.0.0.1:8080/api/v1/recipe/recent_recipes',
            headers: { 
              'Content-Type': 'application/json', 
              'Authorization': `Bearer ${cookie}`
            },
            data : data
          };
          
          axios.request(config)
          .then((response) => {
            console.log("fuck")
            console.log(JSON.stringify(response.data));
          })
          .catch((error) => {
            console.log(error);
          });
          
        
       /*
          var myHeaders = new Headers();
          myHeaders.append("Content-Type", "application/json");
          myHeaders.append("Authorization", `Bearer ${cookie}`);
          
          var raw = JSON.stringify({
            "searchString": 10
          });
          
          var requestOptions = {
            method: 'GET',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
          };
          
          fetch("http://127.0.0.1:8080/api/v1/recipe/recent_recipes", requestOptions)
            .then(response => response.text())
            .then(result => console.log(result))
            .catch(error => console.log('error', error));
            */
    }, []);

    return (
        <div className="Home">
            <Header />
            <h1>Welcome to FoodBlueprints</h1>
            <h3>Create and share your favorite recipes</h3>
        </div>
        
    );
}

export default Home;