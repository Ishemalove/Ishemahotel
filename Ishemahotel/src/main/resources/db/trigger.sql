-- Function to calculate total amount for a booking
CREATE OR REPLACE FUNCTION calculate_booking_amount(
    p_room_id BIGINT,
    p_check_in DATE,
    p_check_out DATE
) RETURNS DECIMAL AS $$
DECLARE
    v_price DECIMAL;
    v_days INTEGER;
BEGIN
    -- Get room price
    SELECT price INTO v_price
    FROM rooms
    WHERE id = p_room_id;

    -- Calculate number of days
    v_days := p_check_out - p_check_in;

    -- Return total amount
    RETURN v_price * v_days;
END;
$$ LANGUAGE plpgsql;

-- Trigger function to create billing record
CREATE OR REPLACE FUNCTION create_billing_record()
RETURNS TRIGGER AS $$
BEGIN
    -- Only create billing for confirmed bookings
    IF NEW.status = 'CONFIRMED' THEN
        INSERT INTO billing (booking_id, amount, generated_at)
        VALUES (
            NEW.id,
            calculate_booking_amount(NEW.room_id, NEW.check_in, NEW.check_out),
            CURRENT_TIMESTAMP
        );
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger
DROP TRIGGER IF EXISTS booking_billing_trigger ON bookings;
CREATE TRIGGER booking_billing_trigger
    AFTER INSERT OR UPDATE OF status
    ON bookings
    FOR EACH ROW
    EXECUTE FUNCTION create_billing_record(); 