import React, { useState, useEffect, useRef } from 'react';
import styles from './SearchBar.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimes } from '@fortawesome/free-solid-svg-icons';

function SearchBar({ searchVisible, onClose }) {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [searching, setSearching] = useState(false);
  const [showNotFound, setShowNotFound] = useState(false);
  const inputRef = useRef(null);
  const delayTimeoutRef = useRef(null);

  useEffect(() => {
    if (searchVisible) {
      inputRef.current.focus();
    }
  }, [searchVisible]);

  const handleSearch = async () => {
    try {
      setSearching(true);

      const response = await fetch(`http://localhost:8080/productos/${searchTerm}`, {
        method: 'GET',
      });

      if (response.ok) {
        const data = await response.json();
        setSearchResults(data);
      } else {
        setSearchResults([]);
      }
    } catch (error) {
      console.error('Error al conectar con el servidor:', error.message);
    } finally {
      setSearching(false);
    }
  };

  const handleInputChange = (e) => {
    const value = e.target.value;
    setSearchTerm(value);

    if (value.trim() === '') {
      setSearchResults([]);
      setSearching(false);
      setShowNotFound(false);
      clearTimeout(delayTimeoutRef.current);
      return;
    }

    clearTimeout(delayTimeoutRef.current);
    delayTimeoutRef.current = setTimeout(() => {
      setShowNotFound(true);
      handleSearch();
    }, 1500);
  };

  return (
    <div className={`${styles.searchBar} ${searchVisible ? styles.show : ''}`}>
      <input
        ref={inputRef}
        type="text"
        placeholder="buscar..."
        value={searchTerm}
        onChange={handleInputChange}
        className={styles.input}
      />
      <FontAwesomeIcon icon={faTimes} className={styles.closeIcon} onClick={onClose} />

      <div className={styles.resultsContainer}>
        {searching && <p>Buscando...</p>}

        {!searching && showNotFound && searchResults.length === 0 && searchTerm.trim() !== '' && (
          <h3 className={styles.notFoundMessage}>No se encontraron resultados.</h3>
        )}

        {!searching && searchResults.length > 0 && (
          <div className={styles.searchResults}>
            <ul className={styles.productList}>
              {searchResults.map((result) => (
                <li key={result.id} className={styles.productItem}>
                  <div className={styles.productoContainer}>
                    <img src={`http://localhost:8080/archivos/${result.imagen}`} alt={result.nombre} />
                    <div>
                      <h3>{result.nombre}</h3>
                      <p>{`$${result.precio}`}</p>
                    </div>
                  </div>
                </li>
              ))}
            </ul>
          </div>
        )}
      </div>
    </div>
  );
}

export default SearchBar;