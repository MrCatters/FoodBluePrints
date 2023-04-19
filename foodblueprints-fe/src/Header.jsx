import './header.css';
import React from "react";


function Header() {
    return(
        <div className="Header">
            <h3>FoodBlueprints</h3>
            <ul className='nav-items'>
                <li>Search</li>
                <li>Add a recipe</li>
                <li>Profile</li>
            </ul>
        </div>
    );
}

export default Header;