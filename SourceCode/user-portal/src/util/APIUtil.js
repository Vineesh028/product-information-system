import { API_BASE_URL } from './Constants';
import axios from 'axios';

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })

    const defaults = { headers: headers };
    options = Object.assign({}, defaults, options);

 if (options.method === 'GET') {

       return  axios.get(
            `${options.url}`,
            {
                headers: {
                    'Content-Type': 'application/json'
                    
                }
            }

        );

    }
};

export function searchAllProducts() {
    return request({
        url: API_BASE_URL + `/v1/products`,
        method: 'GET'
       
    });
}

export function searchProductById(id) {
    return request({
        url: API_BASE_URL + `/v1/product/${id}`,
        method: 'GET'
       
    });
}

export function searchProductByName(name) {
    return request({
        url: API_BASE_URL + `/v1/product/name/${name}`,
        method: 'GET'
       
    });
}