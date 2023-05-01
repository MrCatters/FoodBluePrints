import React from "react";
import Header from "./Header"
import AddPopup from "./AddPopup";
import "./popup.css";
import "./recipecard.css";
import RecipeCard from "./RecipeCard"
import axios from "axios";
import { useState, useEffect } from "react";
import "./home.css"

function Home(){

    const [recipes, setRecipes] = useState();
    const [searchValue, setSearchValue] = useState();
    const [changed,setChanged] = useState(false);
    const [favoriteIDs, setFavoriteIDs] = useState([]);
    const [ping,setPing] = useState();
    
    useEffect(() => {
      setTimeout(function() {
          setPing(true);
      },1000)
  },[])
    useEffect(() => {
        
        const cookiearray = document.cookie.split('=')
        const cookietemp = (cookiearray[1]);
        const cookie = (cookietemp.split(';'))[0]
        let recipes_list;
        let temp = []; 
        
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
            response.data.recipes.forEach(recipe => {
                console.log(recipe)
                temp.push(<RecipeCard id = {recipe.id}key = {recipe.id} title ={recipe.name} author = {recipe.user.firstName} content ={recipe.contents} img = {recipe.image} favorites = {favoriteIDs}></RecipeCard>);
            })
            setRecipes(temp)
          })
          .catch((error) => {
            console.log(error);
          });
          
        
    }, [ping]);

    useEffect(() => {
        
      const cookiearray = document.cookie.split('=')
      const cookietemp = (cookiearray[1]);
      const cookie = (cookietemp.split(';'))[0]
      let temp = [];
      let recipes_list; 
     
      
      let data = JSON.stringify({
        "searchString": `${searchValue}`
      });
      
      let config = {
        method: 'post',
        maxBodyLength: Infinity,
        url: 'http://127.0.0.1:8080/api/v1/recipe/name_recipes',
        headers: { 
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${cookie}`
        },
        data : data
      };
      
      axios.request(config)
      .then((response) => {

        setRecipes([]);
        console.log(JSON.stringify(response.data));
        response.data.recipes.forEach(recipe => {
          temp.push(<RecipeCard id = {recipe.id}key = {recipe.id} title ={recipe.name} author = {recipe.user.firstName} content ={recipe.contents} img = {recipe.image} favorites = {favoriteIDs}></RecipeCard>);
      })
      setRecipes(temp)
      
      })
      .catch((error) => {
        console.log(error);
      });

        
  }, [searchValue]);

    return (
        <div className="outer-container">
          <Header searchActive = {false} changed = {changed} setChanged = {setChanged} searchValue = {searchValue} setSearchValue = {setSearchValue}/>
          <div className="Home">
            <div className="home-topcontent">
               <h1>Welcome to FoodBlueprints</h1>
              <h3>Create and share your favorite recipes</h3>
              <input type='text' className='searchbar' placeholder='Search for a recipe...' onChange={e => setSearchValue(e.target.value)}/>
            </div>
           
            <div className="recent-recipes-container">
                {recipes}
            </div>
          </div>
        </div>
        
    );
}

export default Home;