
import '../style/home.css';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
const Home = () => {

    const [term, setTerm] = useState('');
    const navigate = useNavigate();

    const onFormSubmit = event => {
      event.preventDefault();

      if (term.trim()) {
        navigate(`/search?term=${encodeURIComponent(term.trim())}`);
      }
    };
    
    return (
        <div className="search-container">
         
          <div>
            <h>Search for your product</h>
            
          </div>
          <br/>
          <div class="search-bar">
          <input
            type="text"
            value={term}
            onChange={(e) => setTerm(e.target.value)}
            placeholder="Type your product name..."
          />
          <button onClick={onFormSubmit}>Search</button>
            
          </div>
         
        </div>
      );
    }
  export default Home;
  