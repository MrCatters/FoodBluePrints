import React from "react";
import Header from "./Header"
import AddPopup from "./AddPopup";
import "./home.css"

function Home(){
    return (
        <div className="Home">
            <Header />
            <h1>This website is under construction, sit tight...</h1>
            <AddPopup trigger = { true }>
                <h3>my popup</h3>
            </AddPopup>
        </div>
        
    );
}

export default Home;