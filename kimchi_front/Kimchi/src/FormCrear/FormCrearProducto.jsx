import styles from './FormCrearProducto.module.css';
import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function FormCrearProducto({ onClose }) {
  const [nombre, setNombre] = useState('');
  const [precio, setPrecio] = useState('');
  const [imagen, setImagen] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append('nombre', nombre);
    formData.append('precio', precio);
    formData.append('imagen', imagen);

    try {
      const response = await fetch('http://localhost:8080/productos', {
        method: 'POST',
        body: formData,
      });

      if (response.ok) {
        const data = await response.json();
        // Notificación de éxito
        toast.success(`Producto creado éxito`);
        // Cerrar la ventana modal después de un breve retraso
        setTimeout(() => {
          onClose();
        },);
      } else {
        const error = await response.text();
        console.error(`Error al crear el producto: ${error}`);
      }
    } catch (error) {
      console.error('Error al conectar con el servidor:', error.message);
    }
  };  
  return (
    <>
      <div className={styles.overlay}></div>
      <div className={styles.form}>
        <div className={styles.formContainer}>
          <button className={styles.closeButton} onClick={onClose}>
            <FontAwesomeIcon icon={faTimes} />
          </button>
          <h2>AGREGAR PRODUCTO</h2>
          <form onSubmit={handleSubmit} encType='multipart/form-data'>
            <input
              type="text"
              className={styles.controls}
              value={nombre}
              onChange={(e) => setNombre(e.target.value)}
              required
              placeholder='Nombre'
            />
            <input
              placeholder='Precio'
              type="number"
              className={styles.controls}
              id="precio"
              value={precio}
              onChange={(e) => setPrecio(e.target.value)}
              step="0.01"
              required
            />
            <label>IMAGEN</label>
            <input
              type="file"
              className={styles.controls}
              id="imagen"
              name='imagen'
              onChange={(e) => setImagen(e.target.files[0])}
              required
            />
            <button className={styles.botonsOk} type="submit">
              AGREGAR
            </button>
          </form>
        </div>
      </div>
    </>
  );
}

export default FormCrearProducto;