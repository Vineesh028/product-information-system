import '../style/list.css';
import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { searchProductByName } from '../util/APIUtil';
import { useNavigate } from 'react-router-dom';

const ProductList = () => {
  const [searchResults, setSearchResults] = useState([]);
  const [searchParams] = useSearchParams();
  const query = searchParams.get('term') || '';
  const navigate = useNavigate();

  useEffect(() => {
    if (query) {

      const fetchResults = async () => {
        searchProductByName(query)
          .then(response => {

            const results = response.data;
            setSearchResults(results);
          }).catch(error => {

          });

      };

      fetchResults();
    }
  }, [query]);

  const handleClick = (product) => {
    console.log(product);
    navigate(`/product`, { state: { product } });
  }

  // function handleClick(product) {

  //   console.log(product);
  //   navigate(`/product`, { state: { product } });
  // }

  return (
    <div>
      {query ? (
        <div>
          <h2>Results for: "{query}"</h2>
          <ul>

            {searchResults.map(product => (

              <li key={product.productId} className="list-item" onClick={() => handleClick(product)}>
                <img src={product.imageUrl} alt={' '} class="custom-image"/>
                <h3>{product.productName}</h3>
                <p>{product.description}</p>
              </li>

              // <div key={product.productId} className="grid-item" onClick={() => handleClick(product)}>
              // <div className="item-content">
              //  <h3>{product.productName}</h3>
              //     <p>{product.description}</p>
              //     </div>
              //     </div>

            ))}

          </ul>
        </div>
      ) : (
        <p>No search query provided.</p>
      )}
    </div>
  );
}

export default ProductList;