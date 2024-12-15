-- Insert Usuarios
INSERT INTO `usuarios` (`username`, `password`, `roles`, `email`, `bio`, `fecha_registro`) VALUES ('chefjohn', '$2a$10$L989osrU7uJE3zPCShrhxucC4HZlVaXAuf9h1CgFiFqhNcfLcxl2O', 'user', 'chefjohn@example.com', 'Lover of fine cooking.', '2023-01-01 10:00:00');
INSERT INTO `usuarios` (`username`, `password`, `roles`, `email`, `bio`, `fecha_registro`) VALUES ('janedoe', '$2a$10$L989osrU7uJE3zPCShrhxucC4HZlVaXAuf9h1CgFiFqhNcfLcxl2O', 'user', 'janedoe@example.com', 'Food blogger and recipe enthusiast.', '2023-01-02 11:30:00');
INSERT INTO `usuarios` (`username`, `password`, `roles`, `email`, `bio`, `fecha_registro`) VALUES ('admin', '$2a$10$M4Un/u5Qt6IT4JqKvo50zetnZz1lRGn/vhW8Cy4KVMb96sVq4TG..', 'admin', 'admin@example.com', 'Platform administrator.', '2023-01-01 09:00:00');

-- Insert Ingredientes
INSERT INTO `ingredientes` (`nombre`) VALUES ('Tomato');
INSERT INTO `ingredientes` (`nombre`) VALUES ('Chicken Breast');
INSERT INTO `ingredientes` (`nombre`) VALUES ('Olive Oil');
INSERT INTO `ingredientes` (`nombre`) VALUES ('Garlic');
INSERT INTO `ingredientes` (`nombre`) VALUES ('Basil');
INSERT INTO `ingredientes` (`nombre`) VALUES ('Pasta');

-- Insert Recetas
INSERT INTO `recetas` (`nombre`, `id_usuario`, `fecha_creacion`, `pasos`, `imagen`) VALUES ('Spaghetti Pomodoro', 1, '2023-01-03 12:00:00', '1. Cook pasta. 2. Make sauce. 3. Combine.', NULL);
INSERT INTO `recetas` (`nombre`, `id_usuario`, `fecha_creacion`, `pasos`, `imagen`) VALUES ('Grilled Chicken', 2, '2023-01-04 13:15:00', '1. Marinate chicken. 2. Grill until golden.', NULL);

-- Insert Recetas_Ingredientes
INSERT INTO `recetas_ingredientes` (`id_receta`, `id_ingrediente`, `cantidad`) VALUES (1, 1, '2 cups');
INSERT INTO `recetas_ingredientes` (`id_receta`, `id_ingrediente`, `cantidad`) VALUES (1, 5, '1 tbsp');
INSERT INTO `recetas_ingredientes` (`id_receta`, `id_ingrediente`, `cantidad`) VALUES (1, 6, '200 grams');
INSERT INTO `recetas_ingredientes` (`id_receta`, `id_ingrediente`, `cantidad`) VALUES (2, 2, '300 grams');
INSERT INTO `recetas_ingredientes` (`id_receta`, `id_ingrediente`, `cantidad`) VALUES (2, 3, '2 tbsp');
INSERT INTO `recetas_ingredientes` (`id_receta`, `id_ingrediente`, `cantidad`) VALUES (2, 4, '2 cloves');

-- Insert Favoritos
INSERT INTO `favoritos` (`id_usuario`, `id_receta`, `fecha_agregado`) VALUES (1, 2, '2023-01-05 15:00:00');
INSERT INTO `favoritos` (`id_usuario`, `id_receta`, `fecha_agregado`) VALUES (2, 1, '2023-01-06 16:30:00');

-- Insert Comentarios
INSERT INTO `comentarios` (`id_receta`, `id_usuario`, `comentario`, `fecha_creacion`) VALUES (1, 2, 'Delicious and easy to make!', '2023-01-07 10:00:00');
INSERT INTO `comentarios` (`id_receta`, `id_usuario`, `comentario`, `fecha_creacion`) VALUES (2, 1, 'Perfectly grilled, will make again!', '2023-01-08 11:30:00');
