import './header.css';
import React from "react";
import { useState } from 'react';
import { Link, useNavigate } from "react-router-dom"
import AddPopup from './AddPopup';

function Header() {
    const [popup, setPopup] = useState(false)
    return(
        <div>
            <div className="Header">
                <h3>FoodBlueprints</h3>
                <ul className='nav-items'>
                    <a>Search</a>
                    <a onClick={e => setPopup(true)}>Add a recipe</a>
                    <Link to = "/profile" >Profile</Link>
                </ul>
            </div>
            <AddPopup trigger = { popup } setTrigger = {setPopup}></AddPopup>    
        </div>
       
    );
}

export default Header;