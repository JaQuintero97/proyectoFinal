// Header.jsx
import React, { useState } from 'react';
import styles from './Header.module.css';
import SearchBar from './SearchBar.jsx';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch } from '@fortawesome/free-solid-svg-icons';

function Header({ onAgregarClick }) {
  const [searchVisible, setSearchVisible] = useState(false);

  const toggleSearch = () => {
    setSearchVisible(!searchVisible);
  };

  return (
    <div className={styles.header}>
      <h1>KIMCHI</h1>
      <ul>
        <li>HOME</li>
        <li>SHOP ONLINE</li>
        <li onClick={onAgregarClick}>AGREGAR</li>
      </ul>
      <div className={styles.searchIcon} onClick={toggleSearch}>
        <FontAwesomeIcon className={styles.icon} icon={faSearch} />
      </div>
      <SearchBar searchVisible={searchVisible} onClose={() => setSearchVisible(false)} />
    </div>
  );
}

export default Header;