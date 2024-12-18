
import './style/app.css';
import React from 'react';
import {
  Route,
  Routes
} from 'react-router-dom';
import Home from './components/Home';
import ProductList from './components/ProductList';
import ProductGrid from './components/ProductGrid';

const App = () => {

  return (

    <div>

      <Routes>
        <Route exact path='/' element={<Home />} />
        <Route path="/search" element={<ProductList />} />
        <Route path="/product" element={<ProductGrid />} />

      </Routes>

    </div>

  );
}

export default App;
