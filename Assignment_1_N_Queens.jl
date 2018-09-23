
# Type for defining position of queen(row,col)
type position
    row::Int64
    col::Int64
end

#= Uninformed Depth First Search method to solve N-Queens
    N = No. of rows and columns
    row = current row we are setting queen in
    positions = Array of type position. Contains N no. of position types.
=#
function N_Queens_DFS(N, row, positions)
    
    # Base Case
    if row==N+1
        return true
    end
        
    for col in 1:N
        # Tells if the current cell is safe to keep the queen to avoid attacks
        isSafe=true
        
        i=1
        while i<row
            queen=positions[i]
            # Checking the diagonals, columns and rows on the board if they are safe from attack
            if queen.row==row || queen.col==col || queen.row-queen.col==row-col || queen.row+queen.col==row+col
                isSafe=false # if current cell is found unsafe.
                break
            end
            i+=1
        end
            
        if isSafe
            positions[row]=position(row, col) # Add the current safe cell
            if N_Queens_DFS(N, row+1, positions) # DFS Call
                return true
            end
        end
    end
        
    return false
end

# Function to display the final solution
function displaySolution(N, positions)

    solutionBoard=Array{Char}(N,N)
    for i in 1:N
        for j in 1:N
            solutionBoard[i,j]='*'
        end
    end
    
    for pos in positions
        solutionBoard[pos.row, pos.col]='Q'
    end
    
    println("Solution:")
    println()
    for i in 1:N
        for j in 1:N
            print(solutionBoard[i,j])
        end
        println()
    end
end

N=20
positions = Array{position}(N)

answer=N_Queens_DFS(N,1,positions)
displaySolution(N, positions)
