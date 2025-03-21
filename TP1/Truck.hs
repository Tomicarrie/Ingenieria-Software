module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT )
  where

import Palet
import Stack
import Route

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT cantidadBahias alturaBahias ruta | cantidadBahias <= 0 = error "La cantidad de bahias del camion debe ser un numero entero mayor a 0"
newT cantidadBahias alturaBahias ruta = Tru [newS alturaBahias | s <-[1..cantidadBahias]] ruta


freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
freeCellsT (Tru [] ruta) = 0
freeCellsT (Tru bahias ruta) = sum [freeCellsS bahia | bahia <-bahias]

-- FUNCION AUXILIAR
getStackIndex :: Truck -> Palet -> Int -> Int -- devuelve el indice de la bahia en la que se va a stackear el palet
getStackIndex (Tru bahias ruta) palet idx
  | idx == length bahias = (-1)
  | isValidStack truckStack = idx
  | otherwise = getStackIndex (Tru bahias ruta) palet (idx + 1)
  where
    truckStack = bahias !! idx
    isValidStack bahia = (freeCellsS bahia > 0) 
                        && ((netP palet) + (netS bahia) <= 10)
                        && (holdsS bahia palet ruta)


loadT :: Truck -> Palet -> Truck          -- carga un palet en el camion
loadT (Tru bahias ruta) palet 
  | stackIndex == (-1) = Tru bahias ruta
  | otherwise = Tru (updatedBahias bahias stackIndex palet) ruta
  where
    stackIndex = getStackIndex (Tru bahias ruta) palet 0
    updatedBahias bahias index palet = 
      take (index - 1) bahias ++ 
      [(stackS (bahias !! index) palet)] ++ 
      drop (index + 1) bahias
      

unloadT :: Truck -> String -> Truck   -- responde un camion al que se le han descargado los paletes que podían descargarse en la ciudad
unloadT (Tru [] ruta) ciudad = (Tru [] ruta) 
unloadT (Tru bahias ruta) ciudad =  Tru [popS bahia ciudad | bahia <- bahias] ruta


netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion
netT (Tru [] rutas) = 0
netT (Tru bahias ruta) = sum [netS bahia | bahia <- bahias]
