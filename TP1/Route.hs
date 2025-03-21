module Route ( Route, newR, inOrderR, inRouteR )
  where

data Route = Rou [ String ] deriving (Eq, Show)

newR :: [ String ] -> Route                    -- construye una ruta segun una lista de ciudades
newR [] = error "La ruta debe tener al menos una ciudad"
newR ciudades = Rou ciudades


inOrderR :: Route -> String -> String -> Bool  -- indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta
inOrderR (Rou ciudades) ciudad1 ciudad2 | ciudades == [] = False -- si no encuentra la ciudad1 en la ruta devuelve False
inOrderR (Rou ciudades) ciudad1 ciudad2 | (head ciudades) == ciudad1 = True | (head ciudades) == ciudad2 = False | otherwise = inOrderR (Rou (tail ciudades)) ciudad1 ciudad2


inRouteR :: Route -> String -> Bool             -- indica si la ciudad consultada está en la ruta
inRouteR (Rou ciudades) ciudad | ciudades == [] = False
inRouteR (Rou ciudades) ciudad | (head ciudades) == ciudad = True | otherwise = inRouteR (Rou (tail ciudades)) ciudad

