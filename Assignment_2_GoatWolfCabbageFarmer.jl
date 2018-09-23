
function goal_reach(state)
    return state==[1,1,1,1]
end

function move(state, character)
    if state[character]==0
        state[character]=1
    else
        state[character]=0
    end
end

function is_safe(child)
    # Is man with goat?
    if child[1]==child[2]
        return true
    # Is goat with wolf?    
    elseif child[2]==child[3]
        return false
    # Is goat with cabbage?
    elseif child[2]==child[4]
        return false
    else
        return true
    end
end

function check_safe_child(children, child)
    if is_safe(child)
        push!(children, child)
    end
    return children
end

function expand_state(state)
    child_nodes=[]
    child=copy(state)
    
    #Man has the ability to move alone
    move(child, 1)
    check_safe_child(child_nodes, child)
    
    # For each entity: Goat, Wolf, Cabbage
    for i in 2:4
        # If the entity is on the same side of the man, then move the entity as well as man.
        if state[i]==state[1]
            child=copy(state)
            move(child,1)
            move(child,i)
            check_safe_child(child_nodes, child)
        end
    end
    
    return child_nodes
end

function solution_path(initial_state)
    solution=Array[]
    push!(solution, initial_state)
    next=copy(initial_state)
    while !goal_reach(next)
        next_level=expand_state(next)
        next=[]
        
        for child in next_level
            if !(child in solution)
                next=child
                push!(solution,next)
                break
            end
        end
    end
    
    return solution
end

function print_solution(solution)
    mapping=["left", "right"]
    
    println("Solution: ")
    for sol in solution
        str=string("[Man: ", mapping[sol[1]+1], ", Goat: ", mapping[sol[2]+1], ", Wolf: ", mapping[sol[3]+1], ", Cabbage: ", mapping[sol[4]+1],"]")
        println(str)
    end
end

initial_state=[0,0,0,0] #Order=[Man, Goat, Wolf, Cabbage], 0=>left_side, 1=>right_side

solution = solution_path(initial_state)

print_solution(solution)
