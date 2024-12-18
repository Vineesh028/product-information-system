import '../style/grid.css';
import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import { SOCKET_URL } from '../util/Constants';

import SockJsClient from 'react-stomp';



function ProductGrid() {

  const [message, setMessage] = useState('');
  const location = useLocation();
  const { product } = location.state || {};

  const onConnected = () => {
    console.log("Connected!!")
  }

  const onMessageReceived = (msg) => {
    setMessage(msg.message);
  }

  return (
    <div className="grid-container">
      < div key={product.productId} className="grid-item">
        <img src={product.imageUrl} alt={product.productName} class="custom-image" />
        <div><strong>Product Name:</strong> {product.productName}</div>
        <div><strong>Description:</strong> {product.description}</div>
        <div><strong>Brand:</strong> {product.brand}</div>
        <div><strong>Material:</strong> {product.material}</div>
        <div><strong>Size:</strong> {product.size}</div>
        <div><strong>Colour:</strong> {product.colour}</div>
        <div><strong>Age Group:</strong> {product.ageGroup}</div>
        <div><strong>Category:</strong> {product.category}</div>
        <div><strong>Price:</strong> ${product.price.toFixed(2)}</div>
        <div><strong>Country:</strong> {product.country}</div>
        <div><strong>Quantity:</strong> {product.quantity}</div>

        <div>
          <SockJsClient
            url={SOCKET_URL}
            topics={['/topic/message']}
            onConnect={onConnected}
            onDisconnect={console.log("Disconnected!")}
            onMessage={msg => onMessageReceived(msg)}
            debug={false}
          />
          <div>{message}</div>
        </div>
        <div>
          <p>

          </p>
        </div>
      </div>
    </div>


  );
}

export default ProductGrid;