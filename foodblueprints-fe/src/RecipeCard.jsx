import React from "react";
import "./recipecard.css";

function RecipeCard(props) {
    return (
        <div className="recipe-card">
            <div className="header-container">
                <h3 className="title">{props.title}</h3>
                <h4 className="author">{props.author}</h4>
            </div>
            <p className="content">
                {props.content}
            </p>
            
        </div>  
    );
}

export default RecipeCard