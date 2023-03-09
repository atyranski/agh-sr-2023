import './App.css';
import { useState, useEffect } from 'react';
import Header from './components/header/header';

export default function App() {
  const [items, setItems] = useState([]);



  return (
    <div className="App">
      <div className="main-container">
        <Header setItems={setItems}/>
        <div>
          <ul>
            {items && items.length > 0 && items.map((itemObject, index) => (
                <li key={itemObject.url}>{itemObject.commit.message} {itemObject.sha}</li>
              ))}
          </ul>
        </div>
      </div>
    </div>
  );
}
