import './header.css';
import React from "react";
import { useState } from 'react';
import { Link, useNavigate } from "react-router-dom"
import AddPopup from './AddPopup';

function handleSearch() {

}

function Header(props) {
    const [popup, setPopup] = useState(false);
    const [changed,setChanged] = useState(false);
    const navigate = useNavigate();
    return (props.searchActive) ? (
        <div>
            <div className="Header">
                <Link to = "/home" ><h3>FoodBlueprints</h3></Link>
                <input type='text' className='searchbar' placeholder='Search for a recipe...' onChange={e => props.setSearchValue(e.target.value)}/>
                <ul className='nav-items'>
                    <a onClick={e => setPopup(true)}>Add a recipe</a>
                    <Link to = "/profile" >Profile</Link>
                </ul>
            </div>
            <AddPopup trigger = { popup } setTrigger = {setPopup}></AddPopup>    
        </div>
    ) :<div>
    <div className="Header">
         <Link to = "/home" ><h3>FoodBlueprints</h3></Link>
        <ul className='nav-items'>
            <a onClick={e => setPopup(true)}>Add a recipe</a>
            <Link to = "/profile" >profile</Link>
        </ul>
    </div>
    <AddPopup trigger = { popup } setTrigger = {setPopup}></AddPopup>    
</div> ;
}

export default Header;